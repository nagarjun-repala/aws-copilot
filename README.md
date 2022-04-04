Sample Java application to deploy on ECS Fargate using AWS Copilot.

Prerequisites:
1. AWS account
2. Configure AWS account using access id and secret key. Use profile or configure as default profile
3. Docker
4. AWS copilot cli

Deployment:
1. Run "copilot init" in the root directory and name your application.
2. Select type of service and name the service.
3. Select the path of Dockerfile.

Internally AWS Copilot cli uses CloudFormation service to deploy resources.