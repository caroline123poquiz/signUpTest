package Testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class TestSignUp {

	private static final String VERIFIED = "I HAVE VERIFIED";
	
	public static void main (String[] args) throws InterruptedException {
		//launch firefox browser
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\cpoquiz\\Documents\\geckoFirefox\\geckodriver.exe");	
		
		Map<String, String> map = generateEmails();
		for(Entry<String, String> e: map.entrySet()) {
			WebDriver driver = new FirefoxDriver();
			processRegistration(driver, e.getKey(), e.getValue());
		}
		
	}

	private static void processRegistration(WebDriver driver, String emailAdd, String password) {
		//enter the url 
		driver.get("https://wooow.hooq.tv/welcome");

		try{
			// scroll the page in order to view the sign up button
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.id("singtel-cast-login-modal-mount"));
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);
			//check if the sign up button is displayed
			boolean isDisplayed = driver.findElement(By.linkText("Sign Up")).isDisplayed();			
			if (isDisplayed) {
				System.out.println("Sign-up button is displayed");
				driver.findElement(By.linkText("Sign Up"));
				//check if sign-up button is enabled
				boolean isEnabled = driver.findElement(By.linkText("Sign Up")).isEnabled();
				if (isEnabled) {
					System.out.println("Sign Up Button is enabled");
					//find and click the sign up button in the page
					driver.findElement(By.linkText("Sign Up")).click();
					//find the email address textbox in the page and enter valid email address on it
					driver.findElement(By.id("email")).sendKeys(emailAdd);
					Thread.sleep(2000);
					//find the Done button and click it
					driver.findElement(By.id("submit-button")).click();	

					//open a new tab and enter gmail's url
					Thread.sleep(3000);
					String a = "window.open('http://www.gmail.com','_blank');";
					((JavascriptExecutor)driver).executeScript(a);
					((JavascriptExecutor)driver).executeScript("window.focus();");
					
					ArrayList<String> list = new ArrayList<>(driver.getWindowHandles());
					driver.switchTo().window(list.get(1));
					
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
					//find the email address textbox and enter valid email address on it
					driver.findElement(By.id("identifierId")).sendKeys(emailAdd);
					//click the next button
					driver.findElement(By.id("identifierNext")).click();
					//find the password textbox and enter valid password on it
					WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
					WebDriverWait wait = new WebDriverWait(driver, 23);
					wait.until(ExpectedConditions.elementToBeClickable(passwordField));
					passwordField.sendKeys(password);
					//click the next button
					driver.findElement(By.id("passwordNext")).click();
					Thread.sleep(4000);
					//search for hooq's email and open it
					driver.findElement(By.id("gbqfq")).sendKeys("HOOQ - Your verification code");
					Thread.sleep(4000);
					driver.findElement(By.id("gbqfb")).click();

					List<WebElement> unreadEmail = driver.findElements(By.xpath("//*[@class='zF']"));
					for(WebElement w : unreadEmail) {
						WebDriverWait waitMail = new WebDriverWait(driver, 10);
						waitMail.until(ExpectedConditions.elementToBeClickable(w));
						w.click();
						break;
					}
					//find and click the confirm button in the email
					WebElement confirm = driver.findElement(By.linkText("Confirm Email"));
					WebDriverWait waitMail = new WebDriverWait(driver, 10);
					waitMail.until(ExpectedConditions.elementToBeClickable(confirm));
					Thread.sleep(3000);
					confirm.click();
					
					Thread.sleep(3000);
					ArrayList<String> confirmWindow = new ArrayList<>(driver.getWindowHandles());
					System.out.println(confirmWindow.get(0) +"/" + confirmWindow.get(1) +"/" + confirmWindow.get(2));
					driver.switchTo().window(confirmWindow.get(2));
					
					//check the header title and verify that 'I have verified' is displayed
					WebElement confirmElement = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/h3"));
					WebDriverWait waitConfirm = new WebDriverWait(driver, 10);
					waitConfirm.until(ExpectedConditions.visibilityOf(confirmElement));
					String title = confirmElement.getText();
					if(VERIFIED.equalsIgnoreCase(title)) {
						System.out.println("Verification is completed");
					//display error message when 'I have verified' is not displayed
					} else {
						System.err.println("Verification Message did not match Expected:" + VERIFIED + " Actual:" + title);
					}
					Thread.sleep(3000);
				//display error message when the 'sign up' button is not enabled
				}else{
					System.err.println("BUG: Sign Up Button is NOT Enabled");
				}
			//display error message when the 'sign up' button is not displayed
			}else{
				System.err.println("BUG: Sign-Up button is NOT displayed");
			}
		//display error message when the element does not exist
		} catch(NoSuchElementException e){
			System.out.println("Element not exist" + e);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException occured:" + e);
			
		}

	}

	private static Map<String,String> generateEmails() {
		Map<String, String> emailLoginMap = new HashMap<>();
		emailLoginMap.put("trmr999005@gmail.com", "password@1");
		emailLoginMap.put("trmr999006@gmail.com", "password@1");	
		return emailLoginMap;
	}

} 