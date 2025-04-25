import { Page } from "@playwright/test";
import { Table } from "../../components/Table";

export class BasePage {

    readonly table: Table;

    constructor(readonly page: Page) {
        this.table = new Table(page);
    }

}