curl http://127.0.0.1:3337/healthcheck
echo
echo "-------------------------------------------"

# add
curl -vvv -X PUT -H "Expect:" -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" -d '["aa","AA"]' "http://127.0.0.1:3336/v1/anagram/"
echo
echo "-------------------------------------------"

# search
curl -vvv -GET -H "Expect:" -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" "http://127.0.0.1:3336/v1/anagram/?word=aa"
echo
echo "-------------------------------------------"

# remove
curl -vvv -X DELETE -H "Expect:" -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" "http://127.0.0.1:3336/v1/anagram/?word=aa"
echo
echo "-------------------------------------------"

# search
curl -vvv -GET -H "Expect:" -H "Accept:*/*" -H "Content-Type:application/json" -H "Connection:close" "http://127.0.0.1:3336/v1/anagram/?word=aa"
echo
echo "-------------------------------------------"