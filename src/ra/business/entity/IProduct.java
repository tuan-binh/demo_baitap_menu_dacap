package ra.business.entity;

import java.util.Scanner;

public interface IProduct {
	Float MIN_INTEREST_RATE = 0.2F;
	void inputData(Scanner sc);
	void displayData();
	void calProfit();
}
