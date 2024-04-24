

# Define the directory names to be created

dir1="Background"
dir2="Faces"
dir3="CardBack"

# Loop through all subdirectories of the current directory
for d in */ ; do
    # Check if the directory is a directory to avoid processing files
    if [[ -d "$d" ]]; then
        # Create three directories inside each subdirectory
        mkdir -p "${d}${dir1}"
        mkdir -p "${d}${dir2}"
        mkdir -p "${d}${dir3}"
        echo "Directories created in $d"
    fi
done
