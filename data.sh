for i in {1..50}
do
    name=name-$RANDOM
    age=$(( ( RANDOM % 50 )  + 20 ))
    addr1=addr1-$RANDOM
    addr2=addr2-$RANDOM
    echo "INSERT INTO PLAYER (ID, NAME, AGE, ADDRESS1, ADDRESS2) VALUES($i, '$name', $age, '$addr1', '$addr2');"
done
