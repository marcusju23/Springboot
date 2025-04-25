import { Locator, Page } from "@playwright/test";
import { BasePage } from "./base/BasePage";

export class WelcomePage extends BasePage {

    readonly header: Locator;

    constructor(readonly page: Page) {
        super(page);

        this.header = this.page.locator('.welcome-message');
    }

    async goto() {
        await this.page.goto('http://localhost:8080/web/welcome');
    }

}