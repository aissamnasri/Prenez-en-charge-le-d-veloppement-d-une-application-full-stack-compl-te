import { expect, type Page } from '@playwright/test';

export async function signIn(page: Page): Promise<void> {
  await page.goto('/');
  await page.getByRole('button', { name: 'Se connecter' }).click();
  await page.getByLabel("E-mail ou nom d'utilisateur").fill('marie@example.com');
  await page.getByLabel('Mot de passe').fill('password123');
  await page.getByRole('button', { name: 'Se connecter' }).click();
  await expect(page).toHaveURL(/\/feed$/);
}
