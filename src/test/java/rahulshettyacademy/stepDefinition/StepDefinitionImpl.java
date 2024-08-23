package rahulshettyacademy.stepDefinition;

import java.io.IOException;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on ecommerce page")
	public void I_landed_on_ecommerce_page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
    @When("^I add product (.+) to the cart$")
    public void I_add_product_to_the_cart(String productName) throws InterruptedException
    {
    	productCatalogue.getProductList();
    	productCatalogue.addProductToCart(productName);
    }
    
    @And("^checkout product (.+) and submit the order$")
    public void checkout_product_and_submit_the_order(String productName)
    {
    	CartPage cartPage = productCatalogue.goToCartPage();
    	
    	boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
    }
    
    @Then("{string} message is displayed on the ConfirmationPage")
    // Alternative way: @Then("^\"([^\"]*)\" message is displayed on the ConfirmationPage$") 
    public void  message_is_displayed_on_the_ConfirmationPage(String string)
    {
    	String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
    }
    
    @Then("^\"([^\"]*)\" login error message is displayed")
    public void login_error_message_is_displayed(String loginErrorMsg)
    {
    	Assert.assertEquals(loginErrorMsg, landingPage.getErrorMessage());
    	driver.close();
    }
}
   
