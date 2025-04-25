import { Locator, Page } from "@playwright/test";

export class Table {

    readonly grid: Locator;

    constructor(readonly page: Page) {
        this.grid = this.page.locator('.styled-table');
    }

}