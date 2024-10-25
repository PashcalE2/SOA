export WILDFLY_HOME="/home/studs/s311817/servers/wildfly-27.0.0.Final"
export JETTY_SERVICE_HOME="/home/studs/s311817/servers/jetty_service"
export PWD_DIR=$( pwd )

cd "/home/studs/s311817/certs/"
rm ./wildfly.* ./jetty.*

# Генерируем серверный сертификат
keytool -genkeypair -alias wildfly -keyalg RSA -keysize 2048 -validity 365 -keystore wildfly.keystore -dname "cn=wildfly,o=Acme,c=GB" -keypass secret -storepass secret

# Копируем keystore на сервер приложений
cp wildfly.keystore $JETTY_SERVICE_HOME

# Генерируем клиентский сертификат
keytool -genkeypair -alias jetty -keyalg RSA -keysize 2048 -validity 365 -keystore jetty.keystore -dname "CN=jetty" -keypass secret -storepass secret

# Экспортируем содержимое клиентского и серверного keystore в файлы сертификатов
keytool -exportcert -keystore wildfly.keystore -alias wildfly -keypass secret -storepass secret -file wildfly.crt
keytool -exportcert -keystore jetty.keystore -alias jetty -keypass secret -storepass secret -file jetty.crt

# Импортируем сертификаты в клиентский и серверный truststore
keytool -importcert -keystore wildfly.truststore -storepass secret -alias jetty -trustcacerts -file jetty.crt -noprompt
keytool -importcert -keystore jetty.truststore -storepass secret -alias wildfly -trustcacerts -file wildfly.crt -noprompt

# Копируем клиентский truststore в конфигурацию сервера приложений
cp jetty.truststore $WILDFLY_HOME/standalone/configuration

cd $PWD_DIR
