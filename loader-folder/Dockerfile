# Use the official Python 3.10 image as the base image
FROM python:3.10-slim

# Set the working directory in the container
WORKDIR /gang-scheduler-proj/loader-folder

# Copy the requirements file and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of your Python project
COPY . .

# Command to run your Python application
CMD ["python", "main.py"]
