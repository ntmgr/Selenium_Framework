package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String testProduct = "ZARA COAT 3";
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("nimsui@rsa.com", "Baral#2024");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(testProduct));
	}

	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
}


/*
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email", "nimsui@rsa.com");
		map.put("password", "Baral#2024");
		map.put("productName", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("productName", "ADIDAS ORIGINAL");
 */ 

/*
Adding data sets to Json without manually adjusting the array:

Stephen · Lecture 173 · 7 months ago

At the end of video 174 I found myself wondering about something.  What happens if our Json file includes 3 data sets instead of 2 as shown in the video?  Must we then manually add a data.get(2) to our return array?  What if the Json file has a hundred data items?
I sat down, did some experimenting, and came up with the following solution.  It's pretty short, it automatically adjusts for different numbers of data sets, and I think that it's fairly easy to understand.  Hopefully someone will find this useful.

@DataProvider
public Object[][] getData() throws IOException {
 	List<HashMap<String, String>> data = jsonToMap(System.getProperty("user.dir") + "/src/test/java/rahulshettyacademy/data/PurchaseOrder.json");
	final int limit = data.size();
	Object[][] maps = new Object[limit][1];
	for(int i = 0; i < limit; i++) {
	maps[i][0] = data.get(i);
	}	
	return maps;
}
*/