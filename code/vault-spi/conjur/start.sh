#!/bin/bash
#set -x

FILE=$(mktemp)
trap 'rm -rf -- "$FILE"' EXIT

#make all

podman exec -ti conjur conjurctl account create demoAccount > $FILE

export APIKEY=$(sed -n '/API/ s/.*: *//p' $FILE | tr -d '\r')
echo "APIKEY: $APIKEY\n"

# TODO refactor
echo "creating the conjur stuff..."

TOKEN=$(curl --header "Accept-Encoding: base64" -d "${APIKEY}" http://localhost:18080/authn/demoAccount/admin/authenticate)
#echo "token: $TOKEN"

curl -H "Authorization: Token token=\"${TOKEN}\"" -d "$(< policy.yml)" http://localhost:18080/policies/demoAccount/policy/root
curl -H "Authorization: Token token=\"${TOKEN}\"" -d "password" http://localhost:18080/secrets/demoAccount/variable/ldap/password
#curl -k -H "Authorization: Token token=\"${TOKEN}\"" http://localhost:18080/secrets/demoAccount/variable/ldap/password
