# This file contains instructions to follow before committing

#-----------#
# MANDATORY #
#-----------#

    * check that findbugs & checkstyle don't complain :
        java -jar tools/checkstyle-5.6/checkstyle-5.6-all.jar -c tools/checkstyle.xml $(find src/ -type f)
        tools/findbugs-2.0.2/bin/findbugs -textui -auxclasspath lib/slick.jar bin/
    * run the following command to remove tabs and trailing spaces in source code
        tools/notabs.sh src/
    * ask for a code review and wait for its validation
    * make sure the game RUN properly


#---------------#
# RECOMMENDED #
#---------------#

   * update the "WIP.txt" file tracking work in progress


#----------#
# OPTIONAL #
#----------#
