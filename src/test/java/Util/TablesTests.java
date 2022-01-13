package Util;

import cs601.project4.MySQL.Tables.TicketsTable;
import cs601.project4.MySQL.Tables.UsersTable;
import org.junit.Test;

import static cs601.project4.MySQL.Tables.PasswordsTable.verifyDB;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static org.junit.Assert.assertEquals;

/**
 * TablesTests Class
 */
public class TablesTests {


    // Session TABLE Tests
    // ----------------------------------------------------------------

    @Test
    public void testValidateCookieDBCorrect() {
        assertEquals(true, validateCookieDB("cin7m6jydzys1xbo1mu80cc7p","wabganmazq"));
    }

    @Test
    public void testValidateCookieDBwrong() {
        assertEquals(false, validateCookieDB("cin7m6jydzyfdffdfs1xbo1mu80cc7p","wabganmazq"));
    }

    @Test
    public void testValidateCookieDBempty() {
        assertEquals(false, validateCookieDB("",""));
    }


    // Passwords TABLE Tests
    // ----------------------------------------------------------------


    @Test
    public void testValidateVerifyCredencialsCorrect() {
        assertEquals(true, verifyDB("Barunka838@gmail.com","202cb962ac59075b964b07152d234b70"));
    }

    @Test
    public void testVerifyCredencialswrong() {
        assertEquals(false, verifyDB("Barunka@gmail.com","wabganmazq"));
    }

    @Test
    public void testVerifyCredencialsempty() {
        assertEquals(false, verifyDB("",""));
    }


    @Test
    public void testgetNumTicketsOwnedCorrect() {
        assertEquals(0,  TicketsTable.getNumTicketsOwned("Barunka838@gmail.com",4));
    }

    @Test
    public void testgetNumTicketsOwnedrong() {
        assertEquals(0,  TicketsTable.getNumTicketsOwned("Barunka@gmail.com",0));
    }

    @Test
    public void testgetNumTicketsOwnedempty() {
        assertEquals(0,  TicketsTable.getNumTicketsOwned("",-1));
    }

    // Tickets TABLE Tests
    // ----------------------------------------------------------------

    @Test
    public void testValidateDateCorrect() {
        assertEquals(true, TicketsTable.validateDate("2022-12-12"));
    }

    @Test
    public void testValidateDatewrong() {
        assertEquals(false, TicketsTable.validateDate("2002-23-09"));
    }

    @Test
    public void testValidateDatempty() {
        assertEquals(false, TicketsTable.validateDate(""));
    }

    // Users TABLE Tests
    // ----------------------------------------------------------------

    @Test
    public void testisUserInTableCorrect() {
        assertEquals(true, UsersTable.isUserInTable("Barunka838@gmail.com"));
    }

    @Test
    public void testisUserInTableWrong() {
        assertEquals(false, UsersTable.isUserInTable("Barunka@gmail.com"));
    }
}
