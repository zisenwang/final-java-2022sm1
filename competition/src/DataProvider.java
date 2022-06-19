import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataProvider {
    private List<Member> memberList;
    private List<Bill> billList;
    public DataProvider(String memberFile, String billFile) throws Exception{
        memberList = new ArrayList<>();
        billList = new ArrayList<>();


        Scanner memberFileScanner = new Scanner(new File(memberFile));
        Scanner billFileScanner = new Scanner(new File(billFile));


        while (memberFileScanner.hasNext()){
            Member member = new Member();
            String[] memberInfo = memberFileScanner.nextLine().split(",");
            member.setId(memberInfo[0]);
            member.setName(memberInfo[1]);
            member.setEmail(memberInfo[2]);
            memberList.add(member);
        }
        while (billFileScanner.hasNext()){
            Bill bill = new Bill();
            String[] billInfo = billFileScanner.nextLine().split(",");
            bill.setBillId(billInfo[0]);
            bill.setMemberId(billInfo[1]);
            bill.setAmount(Float.parseFloat(billInfo[2]));
            bill.setUsed(Boolean.parseBoolean(billInfo[3]));
            billList.add(bill);
        }
    }

    public Bill getBill(String id){
        for (Bill bill : billList){
            if (bill.getBillId().equals(id)){
                return bill;
            }
        }
        return null;
    }
}
