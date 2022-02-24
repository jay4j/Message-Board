package com.concordia.message_board;
import com.concordia.message_board.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestProject {

    Attachment testAttachment = new Attachment();
    Post SoenPost = new Post("TestID", "TestID", "TestTitle", "TestContent", "2020/12/7", testAttachment, "Soen");
    Post CompPost = new Post("TestID", "TestID", "TestTitle", "TestContent", "2020/12/7", testAttachment, "Comp");
    Post EncsPost = new Post("TestID", "TestID", "TestTitle", "TestContent", "2020/12/7", testAttachment, "Encs");
    Post ConconrdiaPost = new Post("TestID", "TestID", "TestTitle", "TestContent", "2020/12/7", testAttachment, "Concordia");
    User SoenUser = new User("SoenTestName", "Soen", "TestEmail@gmail.com", "TestPd");
    User CompUser = new User("CompTestName", "Comp", "TestEmail@gmail.com", "TestPd");
    User EncsUser = new User("EncsTestName", "Encs", "TestEmail@gmail.com", "TestPd");
    User ConcordiaUser = new User("ConTestName", "Con", "TestEmail@gmail.com", "TestPd");

    @Test
    public void PostTest()
    {
        System.out.println("---------------Start Post Test---------------");
        Attachment testAttachment = new Attachment();
        Post TestPost = new Post("TestID", "TestID", "TestTitle", "TestContent", "2020/12/7", testAttachment, "Concordia");
        System.out.println("Test Post Created.");
        System.out.println(TestPost.toString());
        System.out.println("---------------Finish Post Test---------------");
    }

    @Test
    public void UserTest()
    {
        System.out.println("---------------Start User Test---------------");
        User TestUser = new User("TestName", "TestId", "TestEmail@gmail.com", "TestPd");
        System.out.println("Test user Created.");
        System.out.println(TestUser.toString());
        System.out.println("---------------Finish User Test---------------");

    }

    @Test
    public void ConcordiaUserTest()
    {
        System.out.println("---------------Start ConcordiaUser Test---------------");
        ConcordiaUser TestConUser = new ConcordiaUser();
        System.out.println("Test Concordia User Created.");
        TestConUser.addPostToList(ConconrdiaPost);
        TestConUser.addUserIdToList(ConcordiaUser.getUserId());
        System.out.println(TestConUser.toString());
        System.out.println("---------------Finish ConcordiaUser Test---------------");

    }

    @Test
    public void EncsUserTest()
    {
        System.out.println("---------------Start EncsUser Test---------------");
        EncsUser TestEncsUser = new EncsUser();
        System.out.println("Test Encs User Created.");
        TestEncsUser.addPostToList(EncsPost);
        TestEncsUser.addUserIdToList(EncsUser.getUserId());
        System.out.println(TestEncsUser.toString());
        System.out.println("---------------Finish EncsUser Test---------------");

    }

    @Test
    public void SoenUsertest()
    {
        System.out.println("---------------Start SoenUser Test---------------");
        SoenUser TestSoenUser = new SoenUser();
        System.out.println("Test Soen User Created.");
        TestSoenUser.addPostToList(SoenPost);
        TestSoenUser.addUserIdToList(SoenUser.getUserId());
        System.out.println(TestSoenUser.toString());
        System.out.println("---------------Finish SoenUser Test---------------");

    }

    @Test
    public void CompUserTest()
    {
        System.out.println("---------------Start CompUser Test---------------");
        CompUser TestCompUser = new CompUser();
        System.out.println("Test Comp User Created.");
        TestCompUser.addPostToList(CompPost);
        TestCompUser.addUserIdToList(CompUser.getUserId());
        System.out.println(TestCompUser.toString());
        System.out.println("---------------Finish CompUser Test---------------");
    }


}
