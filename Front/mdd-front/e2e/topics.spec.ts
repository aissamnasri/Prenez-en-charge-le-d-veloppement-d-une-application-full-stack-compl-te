import { expect, test } from '@playwright/test';

import { mockApi } from './support/api-mocks';
import { signIn } from './support/auth';

test.describe('Topics', () => {
  test('subscribes and unsubscribes from topics', async ({ page }) => {
    const api = await mockApi(page);
    await signIn(page);

    await page.getByRole('link', { name: 'Thèmes' }).click();
    await expect(page).toHaveURL(/\/topics$/);

    const angularTopic = page.locator('.topic-card').filter({
      hasText: 'Angular',
    });
    const rxjsTopic = page.locator('.topic-card').filter({
      hasText: 'RxJS',
    });

    await expect(angularTopic).toBeVisible();
    await angularTopic.getByRole('button', { name: "S'abonner" }).click();

    await expect(angularTopic.getByRole('button', { name: 'Se désabonner' })).toBeVisible();
    expect(api.subscribeRequests).toEqual([1]);

    await rxjsTopic.getByRole('button', { name: 'Se désabonner' }).click();

    await expect(rxjsTopic.getByRole('button', { name: "S'abonner" })).toBeVisible();
    expect(api.unsubscribeRequests).toEqual([2]);
  });
});
