for i in {1..100000}
do
    resp=$(curl -s --url https://randomuser.me/api/?nat=us&inc=gender,name,location)
    #echo $resp

    name=$(echo "$resp" | jq -r '.results[0].name | "\(.first) \(.last)"')
    addr=$(echo "$resp" | jq -r '.results[0].location.street | "\(.number) \(.name)"')
    city=$(echo "$resp" | jq -r '.results[0].location.city')
    age=$(( ( RANDOM % 50 )  + 20 ))
    echo "INSERT INTO PLAYER (ID, NAME, AGE, ADDRESS1, ADDRESS2) VALUES($i, '$name', $age, '$addr', '$city');" >> player-data.sql
done
