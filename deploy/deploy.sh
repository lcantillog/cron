#!/bin/bash
export $(cat .env | xargs)
java -jar cron.jar