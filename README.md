# spicy-mayo

* Create a project on Google Apps Script and upload the [spicy-mayo.js](spicy-mayo.js) (or copy-paste its content to the online editor), renaming it to `spicy-mayo.gs`
* Fill the `spicyApiKey` const with a randomly generated string and deploy the script as a Web App, choosing to execute it as <i>Me <sup><sub>(email)</sub></sup></i>, with access to <i>Anyone</i>
* Use the just obtained Deployment ID, and the Api key generated previously, to add these values to your `~\.gradle\local.properties` file:
```properties
# spicy-mayo Apps Script properties
spicyDeployId="deployment_id_obtained_from_google_apps_script"
spicyApiKey="randomly_generated_key_to_copy_also_in_uploaded_script"
```
