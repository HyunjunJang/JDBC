package com.jdbc.example;

import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Model model = new Model();
//		model.selectOne();
		
		//INSERT
//		System.out.print("부서아이디>");
//		int id = scan.nextInt();
//		System.out.print("부서이름>");
//		String name = scan.next();
//		System.out.print("매니저아이디>");
//		String mid = scan.next();
//		System.out.print("부서아이디>");
//		String lid = scan.next();
//		model.insertOne(id, name, mid, lid);
		
		//UPDATE
//		System.out.print("부서아이디>");
//		int id = scan.nextInt();
//		System.out.print("부서명>");
//		String name = scan.next();
//		System.out.print("매니저아이디>");
//		String mid = scan.next();
//		model.updateOne(id, name, mid);
		
		//DELETE
		System.out.print("EMPLOYEE_ID>");
		int id = scan.nextInt();
		model.deleteOne(id);
		scan.close();
	}

}
