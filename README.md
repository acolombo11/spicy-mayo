# spicy-mayo

* Generate a random password to use as an api-key.
* Create a project on Google Apps Script and upload the spicy-mayo.js, renaming it to spicy-mayo.gs.
* Fill the spicyApiKey with a randomly generated password and deploy the script as a Web App, executed as "Me (your-email)" with access to "Anyone". 
* Use the Deployment ID obtained when deploying the script, and the Api key you generated previously, to add these values to your `~\.gradle\local.properties` file:
```properties
# Spicy-Mayo Apps Script keys
spicyDeployId="deployment_id_obtained_from_google_apps_script"
spicyApiKey="randomly_generated_key_to_copy_also_in_uploaded_script"
```