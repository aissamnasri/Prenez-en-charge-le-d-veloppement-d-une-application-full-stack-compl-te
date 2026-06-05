import { expect, test } from '@playwright/test';

import { mockApi } from './support/api-mocks';
import { signIn } from './support/auth';

test.describe('Profile', () => {
  test('updates profile information and removes a subscription', async ({ page }) => {
    const api = await mockApi(page);
    await signIn(page);

    await page.getByRole('button', { name: 'Profil' }).click();
    await expect(page).toHaveURL(/\/profile$/);

    await expect(page.getByRole('heading', { name: 'Profil utilisateur' })).toBeVisible();
    await page.getByPlaceholder('Username').fill('marie-curie');
    await page.getByPlaceholder('email@email.fr').fill('curie@example.com');
    await page.getByPlaceholder('Mot de passe').fill('newpassword123');
    await page.getByRole('button', { name: 'Sauvegarder' }).click();

    expect(api.updateUserRequests).toEqual([
      {
        username: 'marie-curie',
        email: 'curie@example.com',
        password: 'newpassword123',
      },
    ]);

    const rxjsSubscription = page.locator('.subscription-card').filter({
      hasText: 'RxJS',
    });

    await expect(rxjsSubscription).toBeVisible();
    await rxjsSubscription.getByRole('button', { name: 'Se désabonner' }).click();

    await expect(rxjsSubscription).toBeHidden();
    expect(api.unsubscribeRequests).toEqual([2]);
  });
});
