package Testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class TestSignUp {
	//private static final String EMAIL_ADD = "@gmail.com";
	//private static final String NAME = "carolinesalada111";
	//private static final String NEW_URL = "www.gmail.com";
	//private static final String PASS = "password@1";
	//private static final String VERIFIED = "I HAVE VERIFIED";

	public static void main (String[] args) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\cpoquiz\\Documents\\geckoFirefox\\geckodriver.exe");	
		//launch firefox browser
		
		Map<String, String> map = generateEmails();
		for(Entry<String, String> e: map.entrySet()) {
			WebDriver driver = new FirefoxDriver();
			processRegistration(driver, e.getKey(), e.getValue());
		}
		
	}

	private static void processRegistration(WebDriver driver, String emailAdd, String password) {
		//open hooq website
		driver.manage().window().maximize();
		driver.get("https://wooow.hooq.tv/welcome");

		//String emailAdd = NAME.concat(EMAIL_ADD);
		try{
			// scroll the page here
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.id("singtel-cast-login-modal-mount"));
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);

			boolean isDisplayed = driver.findElement(By.linkText("Sign Up")).isDisplayed();
			System.out.println("isDisplayed value:::::" + isDisplayed);
			if (isDisplayed) {
				System.out.println("Sign-up button is displayed");
				driver.findElement(By.linkText("Sign Up"));
				//check if sign-up button is enabled
				boolean isEnabled = driver.findElement(By.linkText("Sign Up")).isEnabled();
				if (isEnabled) {
					System.out.println("Sign Up Button is enabled");
					driver.findElement(By.linkText("Sign Up")).click();
					driver.findElement(By.id("email")).sendKeys(emailAdd);
					Thread.sleep(2000);
					driver.findElement(By.id("submit-button")).click();	

					//
					Thread.sleep(3000);
					String a = "window.open('http://www.gmail.com','_blank');";
					((JavascriptExecutor)driver).executeScript(a);
					((JavascriptExecutor)driver).executeScript("window.focus();");
					//Thread.sleep(2000);
					ArrayList<String> list = new ArrayList<>(driver.getWindowHandles());
					driver.switchTo().window(list.get(1));
					
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
					driver.findElement(By.id("identifierId")).sendKeys(emailAdd);
					driver.findElement(By.id("identifierNext")).click();

					WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
					WebDriverWait wait = new WebDriverWait(driver, 23);
					wait.until(ExpectedConditions.elementToBeClickable(passwordField));
					passwordField.sendKeys(password);
					
					driver.findElement(By.id("passwordNext")).click();
					Thread.sleep(4000);
					driver.findElement(By.id("gbqfq")).sendKeys("HOOQ - Your verification code");
					driver.findElement(By.id("gbqfb")).click();

					List<WebElement> unreadEmail = driver.findElements(By.xpath("//*[@class='zF']"));
					for(WebElement w : unreadEmail) {
						WebDriverWait waitMail = new WebDriverWait(driver, 23);
						waitMail.until(ExpectedConditions.elementToBeClickable(w));
						w.click();
						break;
					}
					WebElement confirm = driver.findElement(By.linkText("Confirm Email"));
					WebDriverWait waitMail = new WebDriverWait(driver, 15);
					waitMail.until(ExpectedConditions.elementToBeClickable(confirm));
					Thread.sleep(3000);
					confirm.click();
					
					driver.switchTo().activeElement();
					
					/*WebElement confirmElement = driver.findElement(By.className("card-title"));
					WebDriverWait waitConfirm = new WebDriverWait(driver, 10);
					waitConfirm.until(ExpectedConditions.textToBePresentInElement(confirmElement, "I have verified"));
					String title = confirmElement.getText();
					if(!VERIFIED.equalsIgnoreCase(title)) {
						System.err.println("Verification Message did not match");
					}
					Thread.sleep(3000);*/
				}else{
					System.err.println("BUG: Sign Up Button is NOT Enabled");
				}

			}else{
				System.err.println("BUG: Sign-Up button is NOT displayed");
			}

		} catch(NoSuchElementException e){
			System.out.println("Element not exist" + e);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException occured:" + e);
		} finally {	
			try {
				Thread.sleep(3000);
				driver.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	private static Map<String,String> generateEmails() {
		Map<String, String> emailLoginMap = new HashMap<>();
		emailLoginMap.put("trmr999005@gmail.com", "password@1");
		emailLoginMap.put("trmr999006@gmail.com", "password@1");
		return emailLoginMap;
	}

} 