# 💰 Expense Tracker

📊 Keep Your Finances in Check!
Expense Tracker is a powerful web application designed to help you manage your personal or business expenses with ease.
Built with modern technologies and containerized for seamless deployment, this app is your go-to solution for financial
management.

## 🚀 Key Features

**Intuitive Expense Logging:** Quickly add and categorize your expenses

**Reports:** Get insights into your spending habits with reports

**Budget Planning:** Set and track your budgets effortlessly

## 🛠️ Technologies Used

+ Java 17

+ Spring Boot 3

+ Docker

+ Docker Compose

+ PostgreSQL

## 🔧 Installation

Get Expense Tracker up and running in no time with these simple steps:

1. Clone the repository:
   git clone https://github.com/RezaGholamzad/Expense-Tracker

2. Navigate to the project directory:
   <code> cd Expense-Tracker </code>

3. Build the application jar file:
   <code>mvn clean package</code>

4. run the application using Docker Compose:
   <code>docker-compose up --build -d</code>

5. Access the application at http://localhost:8080

## 🚢 Deployment

Expense Tracker is packaged as a Docker container, ensuring consistency across different environments. Here's how it's
set up:

+ The application is built using a custom Dockerfile

+ Both the app and PostgreSQL database are defined in a docker-compose.yml file

+ Easily deployable on any server supporting Docker containers

### To deploy:

1. Ensure Docker and Docker Compose are installed on your server

2. Copy the project files to your server

3. Run the following command:

4. docker-compose up -d

## 💡 Usage

1. Open your browser and go to http://localhost:8080

2. log in

3. Start tracking your expenses and managing your finances!

## 🤝 Contributing

We welcome contributions! If you'd like to improve Expense Tracker:

1. Fork the repository

2. Create a new branch (git checkout -b feature/AmazingFeature)

3. Commit your changes (git commit -m 'Add some AmazingFeature')

4. Push to the branch (git push origin feature/AmazingFeature)

5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.
You are free to use, modify, and distribute this software for both personal and commercial purposes,
provided that all copies include the original copyright notice and this permission notice.