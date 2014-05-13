FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}"

SRC_URI_sh4 = "${DEBIAN_MIRROR}/main/liba/libaio/libaio_${PV}.orig.tar.gz \
    file://00_arches.patch \
    file://00_arches_sh.patch \
    file://00_arches_sparc64.patch \
    file://01_link_libgcc.patch \
    file://02_libdevdir.patch \
    file://03_man_errors.patch \
    file://03_man_escape_backslash.patch \
    file://04_check_waitpid_return.patch \
    file://04_no_Werror.patch \
    file://05_build-flags.patch \
    file://libaio-0.3.109-cross-install.patch \
    file://toolchain.patch \
"
