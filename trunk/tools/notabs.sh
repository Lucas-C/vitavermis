# Replace tabs by 4 spaces & remove trailing ones & spaces

findTxt () {            # Find only text files
    find "$@" -type f -exec file {} \; | grep text | cut -d':' -f1
}

for f in $(findTxt "$@"); do
    sed -i -e 's/[     ]*$//g' "$f"
    sed -i -e 's/    /    /g' "$f"
done

