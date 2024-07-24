package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryRemoveAndSearchMembersTest {

    Library library;
    int toRemoveID;

    ArrayList<Member> members;

    @BeforeEach
    void setUp() {
        library = Library.getInstance();
        this.members = new ArrayList<>();
        toRemoveID = library.addMember("member3", "0504616222");
        this.members.add(library.getMemberById(toRemoveID));
        this.members.add(library.getMemberById(library.addMember("member1", "0504616223")));

    }

    @AfterEach
    void tearDown() {
        this.library.resetLibrary();
        this.members.clear();
    }

    @Test
    void removeMember() {
        Member member = library.getMemberById(toRemoveID);
        assertTrue(library.getAllMembers().contains(member));
        library.removeMember(toRemoveID);
        assertFalse(library.getAllMembers().contains(member));
    }


    @Test
    void getAllMembers() {
        assertTrue(this.library.getAllMembers().containsAll(this.members), "All my members should be in the library");
        assertEquals(this.library.getAllMembers().size(), this.members.size(), "Library should only contain my members");
    }

    @Test
    void getMemberById() {
        String name = "name";
        String phoneNumber = "0584616222";
        int year = 2022;
        int memberId = library.addMember(name, phoneNumber);
        Member member = library.getMemberById(memberId);
        assertTrue(name.equals(member.getName()) && phoneNumber.equals(member.getPhoneNumber()));
        assertEquals(member.getMemberId(), memberId);
    }
    @Test
    void getMembersByName() {
        String name = "member3";
        assertEquals(1, library.getMembersByName(name).size(), "Expected 1 members with title 'member3'");
        name = "member";
        assertEquals(2, library.getMembersByName(name).size(), "Expected 2 members with titles containing 'member'");
        name = "not existing";
        assertEquals(0, library.getMembersByName(name).size(), "Expected 0 members with titles containing 'not existing'");

    }

    @Test
    void getMembersByPhone() {
        String phoneNumber = "0504616222";
        assertEquals(1, library.getMembersByPhone(phoneNumber).size(), "Expected 1 members with title '0581616999'");
        phoneNumber = "05";
        assertEquals(2, library.getMembersByPhone(phoneNumber).size(), "Expected 2 members with titles containing '05'");
        phoneNumber = "0564616222";
        assertEquals(0, library.getMembersByPhone(phoneNumber).size(), "Expected 0 members with titles containing '46545484848'");
        phoneNumber = "";
        assertNull(library.getMembersByPhone(phoneNumber), "Expected 0 members with titles containing '' (empty)");
        phoneNumber = "invaild phone";
        assertNull(library.getMembersByPhone(phoneNumber), "Expected 0 members with titles containing 'invalid phone'");

    }

}