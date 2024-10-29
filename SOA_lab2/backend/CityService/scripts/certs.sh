export WILDFLY_HOME="/home/studs/s311817/servers/wildfly-27.0.0.Final"
export JETTY_SERVICE_HOME="/home/studs/s311817/servers/jetty_service"
export PWD_DIR=$( pwd )

cd "/home/studs/s311817/certs/"
rm ./wildfly.* ./jetty.* 
rm $WILDFLY_HOME/standalone/configuration/wildfly.* $WILDFLY_HOME/standalone/configuration/jetty.*
rm $JETTY_SERVICE_HOME/wildfly.* $JETTY_SERVICE_HOME/jetty.*

# Генерируем серверный keystore
keytool -genkeypair -alias wildfly -keyalg RSA -keysize 2048 -validity 365 -keystore wildfly.keystore -dname "cn=wildfly" -ext SAN=dns:localhost -keypass secret -storepass secret

# Генерируем клиентский keystore
keytool -genkeypair -alias jetty -keyalg RSA -keysize 2048 -validity 365 -keystore jetty.keystore -dname "cn=jetty" -ext SAN=dns:localhost -keypass secret -storepass secret

# Экспортируем содержимое серверного и клиентского keystore в файлы сертификатов
keytool -exportcert -keystore wildfly.keystore -alias wildfly -keypass secret -storepass secret -file wildfly.crt
keytool -exportcert -keystore jetty.keystore -alias jetty -keypass secret -storepass secret -file jetty.crt

# Импортируем сертификаты в серверный и клиентский truststore
keytool -importcert -keystore wildfly.truststore -storepass secret -alias wildfly -trustcacerts -file jetty.crt -noprompt
keytool -importcert -keystore jetty.truststore -storepass secret -alias jetty -trustcacerts -file wildfly.crt -noprompt

# Копируем keystore на сервер приложений
ln wildfly.keystore $WILDFLY_HOME/standalone/configuration

# Копируем keystore на зависимый сервер
ln jetty.keystore $JETTY_SERVICE_HOME

# Копируем crt на сервер приложений
# ln wildfly.crt $WILDFLY_HOME/standalone/configuration

# Копируем crt на зависимый сервер
# ln jetty.crt $JETTY_SERVICE_HOME

# Копируем серверный truststore в конфигурацию сервера приложений
ln wildfly.truststore $WILDFLY_HOME/standalone/configuration

# Копируем клиентский truststore в конфигурацию зависимого сервера
ln jetty.truststore $JETTY_SERVICE_HOME

cd $PWD_DIR
