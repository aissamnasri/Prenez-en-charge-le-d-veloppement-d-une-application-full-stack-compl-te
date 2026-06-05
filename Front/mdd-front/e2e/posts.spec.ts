import { expect, test } from '@playwright/test';

import { mockApi } from './support/api-mocks';
import { signIn } from './support/auth';

test.describe('Posts', () => {
  test('creates a post from the feed', async ({ page }) => {
    const api = await mockApi(page);
    await signIn(page);

    await page.getByRole('button', { name: 'Créer un article' }).click();

    await expect(page).toHaveURL(/\/posts\/create$/);
    await page.getByRole('combobox', { name: 'Topic' }).click();
    await page.getByRole('option', { name: 'Angular' }).click();
    await page.getByLabel('Titre').fill('Nouveau guide Angular');
    await page.getByLabel('Contenu').fill('Un contenu redige depuis un test e2e Playwright.');
    await page.getByRole('button', { name: 'Créer' }).click();

    await expect(page).toHaveURL(/\/feed$/);
    await expect(page.getByText('Nouveau guide Angular')).toBeVisible();
    expect(api.createPostRequests).toEqual([
      {
        topicId: 1,
        title: 'Nouveau guide Angular',
        content: 'Un contenu redige depuis un test e2e Playwright.',
      },
    ]);
  });
});
