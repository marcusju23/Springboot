import { test, expect } from '@playwright/test';
import { WelcomePage } from "../../pages/WelcomePage";

let welcomePage: WelcomePage;

test.beforeEach(async ({ page }) => {
    welcomePage = new WelcomePage(page);
    await welcomePage.goto();
});

test('table is visible', async ({ page }) => {
    await expect(welcomePage.table.grid).toBeVisible();
});

test('header is visible', async ({ page }) => {
    await expect(welcomePage.header).toBeVisible();
});
