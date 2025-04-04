# Clothing store EJB

---

## How to run project

1. Install Docker & Docker compose
2. Run `docker-compose.yml` file to create an instance of PostgreSQL database.

    Open terminal and change directory to the `docker-compose.yml` file. Then run the command:

    ```bash
    docker compose up --build
    ```

3. Install Intellij IDE.
4. Config Intellij IDE to use WildFly server included in the repo.
    Step 1: Add new run configuration
    ![](/docs/imgs/add-configuration-for-project-1.png)

    Step 2: Configure application server
    ![](/docs/imgs/add-configuration-for-project-2.png)

    Step 3: Locate the WildFly server included in the repo.
    ![](/docs/imgs/add-configuration-for-project-3.png)

    Success result should look like the image below.
    ![](/docs/imgs/add-configuration-for-project-4.png)

    Step 4: Switch to "Deployment" tab to select artifact to deploy.
    ![](/docs/imgs/add-configuration-for-project-5.png)

    Step 5: Select artifact and press "OK" to complete process.
    ![](/docs/imgs/add-configuration-for-project-6.png)
    
    The result configuration should look like the image below.
    ![](/docs/imgs/add-configuration-for-project-7.png)

5. Now you can Run the application and server using the "Run" button.
    ![](/docs/imgs/run-btn.png)
