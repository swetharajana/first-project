package Minorproject;
import java.sql.*;
import java.util.Scanner;

public class Access {
	static Connection connect= connectiondb.getConnection();
	static String sql=" ";
	static int ac=100;
	public boolean loginAccount(Transfer transfer)throws Exception
	{
	
	
		String u_name= transfer.getUser_name().toUpperCase();
	     //return false;
		int pin=transfer.getPin();
		sql="select * from banking3 where user_name='"+u_name+"' and pin="+pin;
		PreparedStatement st=connect.prepareStatement(sql);
		ResultSet rs=st.executeQuery(sql);
		Scanner scan1=new Scanner(System.in);

		if(rs.next())
		{
			int snt=rs.getInt("acc_no");
			int cho=0;
			while(true)
			{
			    try
			     {
					System.out.println("Login successful");
					System.out.println("hello, "+rs.getString("user_name"));
					System.out.println("1.Check Balance");
					System.out.println("2.Withdraw Amount");
					System.out.println("3.Deposit Amount");
					System.out.println("4.Logout");
					System.out.println("Enter your choice: ");
					cho=scan1.nextInt();
					if(cho==1)
					{
						Access access = new Access();
						access.getBalance(snt);
					}
					else if(cho==2)
					{
						Access access = new Access();
						access.withdrawAmount(snt);
						
					}
					else if(cho==3)
					{
						Access access = new Access();
						access.depositAmount(snt);
					}
					else
					{
						System.out.println("Thank you, Have a nice day!");
						break;
					}
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
            
			}
		}
		return false;
	}
public boolean createAccount(Transfer transfer) throws Exception
{
	
	
	String u_name=transfer.getUser_name().toUpperCase();
	Integer pin=transfer.getPin();
	try
	{
		if(u_name=="" || pin==0)
		{
			System.out.println("All Fields Required");
			return false;
		}


		ac+=1;
		sql="insert into banking3(acc_no,user_name,balance,pin) values("+ac+",'"+u_name+"' ,500, "+pin+")";
		Connection connect1=connectiondb.getConnection();
		PreparedStatement st1=connect1.prepareStatement(sql);
			if(st1.executeUpdate(sql)==1)
			{
				System.out.println("Welcome,  "+u_name);
				return true;
			}
	}catch(SQLIntegrityConstraintViolationException e)
	{
		System.out.println("Username Not Available");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return false;
}
public void getBalance(int accountNumber) throws Exception 
{
	try
	{
	 	sql= "select * from banking3 where acc_no="+accountNumber;
		PreparedStatement st= connect.prepareStatement(sql);
		ResultSet rs= st.executeQuery(sql);
		
		while(rs.next()) {
			System.out.println(rs.getInt("acc_no")+" : " +rs.getString("balance")); //"%12d  %10s %10s\n", rs.getInt("acc_no"), rs.getString("user_name"), rs.getString("acc_balance"))
				
			}
			System.out.print("..........................................................");
			
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}

public void withdrawAmount(int accountNumber) throws Exception
{
    try
    {
       Scanner scan1=new Scanner(System.in);
       sql="select * from banking3 where acc_no="+accountNumber;
       PreparedStatement st=connect.prepareStatement(sql);
       ResultSet rs=st.executeQuery(sql);

       while(rs.next()) {
             
            System.out.println("Your Available Balance is : "+rs.getInt("balance"));
            System.out.println("Enter the withdrawing amount : ");
            int withdraw=scan1.nextInt();
            if(Integer.parseInt(rs.getString("balance"))<withdraw || withdraw<=0)
       {
            System.out.println("Invalid amount, Insufficient Balance");
            return;
       }
       int finalAmount=Integer. parseInt(rs.getString("balance"))-withdraw;
       Statement sta=connect.createStatement();
       sql="update banking3 set balance='"+finalAmount+"' where acc_no="+accountNumber;
       sta.executeUpdate(sql);
       System.out.println("withdraw successful");
       System.out.println("Updated Balance");
       System.out.println();
       Access access=new Access();
       access.getBalance(accountNumber);
       break;
       }
       
    }
    catch(Exception ee)
    {
        ee.printStackTrace();
    }
    
}


public void depositAmount(int accountNumber) throws Exception
{
    try
    {
        Scanner scan1=new Scanner(System.in);
        sql="select * from banking3 where acc_no="+accountNumber;
        PreparedStatement st=connect.prepareStatement(sql);
        ResultSet rs=st.executeQuery(sql);
        while(rs.next()) {
            System.out.println("Your Available Balance is : "+rs.getString("balance"));
            System.out.println("Enter the deposite amount : ");
            int deposit= scan1.nextInt();
            if(deposit<=0)
        {
            System.out.println("Invalid Amount");
            return;
        }
        
        int finalAmount=Integer.parseInt(rs.getString("balance"))+deposit;
        Statement sta=connect.createStatement();
        sql="update banking3 set balance='"+finalAmount+"' where acc_no="+accountNumber;
        sta.executeUpdate(sql);
        System.out.println("Deposit Successful");
        System.out.println("Update Balance");
        System.out.println();
        Access access=new Access();
        access.getBalance(accountNumber);
        break;
        }
        
    }
    catch(Exception ee)
    {
        ee.printStackTrace();  
    }
}
 

}
