import java.util.ArrayList;

public class MemberFactory {

        private ArrayList<Member> members;

        public MemberFactory( ArrayList<Member> libraryMembers) {
            this.members = libraryMembers;
        }

        public Member createMember(String name, String phoneNumber) {
            // Check if a member with the same name and phone number exists
            for (Member existingMember : this.members) {
                if (existingMember.getName().equals(name) && existingMember.getPhoneNumber().equals(phoneNumber)) {
                    return existingMember;
                }
            }

            // Create a new member
            Member newMember = new Member(name, phoneNumber);
            this.members.add(newMember);
            return newMember;
        }

}
