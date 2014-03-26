require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PR = "r2"

SRC_URI += "file://linuxdvb.patch \
    file://ppp.patch \
    file://types.patch \
    "
