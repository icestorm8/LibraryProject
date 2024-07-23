package Logic;

import java.util.ArrayList;

public class MemberFactory {

        private ArrayList<Member> members;

        public MemberFactory( ArrayList<Member> libraryMembers) {
            this.members = libraryMembers;
        }

    /**
     * returns the new member/ existing one if alrady exists
     * @param name name of the member
     * @param phoneNumber phone number
     * @return Member (existing one or new one)
     */
    public Member createMember(String name, String phoneNumber) {
            // Check if a member with the same name and phone number exists
            for (Member existingMember : this.members) {
                if (existingMember.getName().equals(name) && existingMember.getPhoneNumber().equals(phoneNumber)) {
                    System.out.println("member already exists");
                    return existingMember;
                }
            }

            // Create a new member
            Member newMember = new Member(name, phoneNumber);
            this.members.add(newMember);
            return newMember;
        }

}
