# MeX
MeX (Mobile Expense Claim) is an android application that will allow employees to enter the expense details in terms of description, type(Transportation, Food or Rent bill) and attach a picture of bill either from the library/gallery or by capturing a photo from the camera. Upon submitting the claim, the entire data along with attached photos is uploaded to the server and queued for approval automatically by the relevant authority (here manager). Employee can also view the status of all pending claims. Upon approval or disapproval, a confirmation email with the bill details is sent to the employee. System date and Name (of user who is currently logged in) is automatically attached to the claim data for future references.

Tools and Technologies: Java, Python, Flask, Android Studio, IBM Cloud (Hosted on IBM Cloud)

## Prerequisites to run the project
1. Remove the Server folder from the project and then create APK by importing it in Android Studio.
2. Host the server.py file from the Server folder and update the Rest API links accordingly in the project.
