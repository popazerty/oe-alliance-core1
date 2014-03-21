require gcc-${PV}.inc
require gcc-configure-target.inc
require gcc-package-target.inc

ARCH_FLAGS_FOR_TARGET += "-isystem${STAGING_INCDIR}"

SRC_URI_append_sh4 = "\
    file://gcc-4.6.3-stm-120618.patch \
    "
