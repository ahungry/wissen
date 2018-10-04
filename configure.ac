#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.69])
AC_INIT([Wissen], [0.0.1], [m@ahungry.com])

AC_CONFIG_HEADERS([])
AC_CONFIG_SRCDIR([])

# Checks for programs.
AC_PROG_LN_S
AC_PROG_MAKE_SET

# Check for custom binaries that we require for the make commands
AC_CHECK_PROG(SQLITE3_CHECK, sqlite3, yes)
if test x"$SQLITE3_CHECK" != x"yes" ; then
    AC_MSG_ERROR([Please install sqlite3.])
fi

AC_CHECK_PROG(BASH_CHECK, bash, yes)
if test x"$BASH_CHECK" != x"yes" ; then
    AC_MSG_ERROR([Please install bash.])
fi

AC_CONFIG_FILES([Makefile])
AC_OUTPUT
