set -o allexport
source .env
set +o allexport
java -jar cron.jar --spring.profiles.active=prod