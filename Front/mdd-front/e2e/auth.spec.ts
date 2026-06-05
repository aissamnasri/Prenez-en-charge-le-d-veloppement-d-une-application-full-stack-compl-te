import { expect, test } from '@playwright/test';

import { authToken, mockApi } from './support/api-mocks';

test.describe('Authentication', () => {
  test('redirects anonymous users from protected pages to login', async ({ page }) => {
    await page.goto('/feed');

    await expect(page).toHaveURL(/\/login$/);
    await expect(page.getByRole('heading', { name: 'Se connecter' })).toBeVisible();
  });

  test('registers a user and returns to login', async ({ page }) => {
    const api = await mockApi(page);

    await page.goto('/');
    await page.getByRole('button', { name: "S'inscrire" }).click();

    await expect(page).toHaveURL(/\/register$/);
    await page.getByLabel("Nom d'utilisateur").fill('marie');
    await page.getByLabel('Adresse e-mail').fill('marie@example.com');
    await page.getByLabel('Mot de passe').fill('password123');
    await page.getByRole('button', { name: "S'inscrire" }).click();

    await expect(page).toHaveURL(/\/login$/);
    expect(api.registerRequests).toEqual([
      {
        username: 'marie',
        email: 'marie@example.com',
        password: 'password123',
      },
    ]);
  });

  test('logs in, opens the feed and logs out', async ({ page }) => {
    const api = await mockApi(page);

    await page.goto('/');
    await page.getByRole('button', { name: 'Se connecter' }).click();
    await page.getByLabel("E-mail ou nom d'utilisateur").fill('marie@example.com');
    await page.getByLabel('Mot de passe').fill('password123');
    await page.getByRole('button', { name: 'Se connecter' }).click();

    await expect(page).toHaveURL(/\/feed$/);
    await expect(page.getByText('Bienvenue sur MDD')).toBeVisible();
    await expect.poll(() => page.evaluate(() => localStorage.getItem('token'))).toBe(authToken);
    expect(api.loginRequests).toEqual([
      {
        emailOrUsername: 'marie@example.com',
        password: 'password123',
      },
    ]);

    await page.getByRole('button', { name: 'Se déconnecter' }).click();

    await expect(page).toHaveURL(/\/login$/);
    await expect.poll(() => page.evaluate(() => localStorage.getItem('token'))).toBeNull();
  });
});
