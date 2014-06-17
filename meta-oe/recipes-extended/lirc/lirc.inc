DESCRIPTION = "LIRC is a package that allows you to decode and send infra-red signals of many commonly used remote controls."
DESCRIPTION_append_lirc = " This package contains the lirc daemon, libraries and tools."
DESCRIPTION_append_lirc-exec = " This package contains a daemon that runs programs on IR signals."
DESCRIPTION_append_lirc-remotes = " This package contains some config files for remotes."
SECTION = "console/network"
PRIORITY = "optional"
HOMEPAGE = "http://www.lirc.org"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel"
RDEPENDS_lirc-exec = "lirc"
RRECOMMENDS_${PN} = "lirc-exec kernel-module-uinput"

INCPR = "r5"

SRC_URI = "http://prdownloads.sourceforge.net/lirc/lirc-${PV}.tar.bz2"

S = "${WORKDIR}/lirc-${PV}"

PARALLEL_MAKE = ""

CFLAGS_append = " -DUINPUT_NEUTRINO_HACK "

EXTRA_OECONF += "--with-kerneldir=${STAGING_KERNEL_DIR} ${DRIVER} --without-x --with-driver=userspace "

inherit autotools module-base update-rc.d

INITSCRIPT_PACKAGES = "lirc lirc-exec"
INITSCRIPT_NAME = "lircd"
INITSCRIPT_PARAMS = "defaults 20"
INITSCRIPT_NAME_lirc-exec = "lircexec"
INITSCRIPT_PARAMS_lirc-exec = "defaults 21"

EXTRA_OEMAKE = 'SUBDIRS="daemons tools"'

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install ${WORKDIR}/lircd.init ${D}${sysconfdir}/init.d/lircd
    install ${WORKDIR}/lircexec.init ${D}${sysconfdir}/init.d/lircexec
    install -d ${D}${datadir}/lirc/
    cp -pPR ${S}/remotes ${D}${datadir}/lirc/
    rm -rf ${D}/dev
    rm -rf  ${D}/bin/pronto2lirc
}

PACKAGES =+ "lirc-exec lirc-remotes"

FILES_${PN}-dbg += "${bindir}/.debug ${sbindir}/.debug"
FILES_${PN}-dev += "${libdir}/liblirc_client.so"
FILES_${PN} = "${bindir} ${sbindir} ${libdir}/lib*.so.* ${sysconfdir} ${exec_prefix}/var"
FILES_lirc-exec = "${bindir}/irexec ${sysconfdir}/init.d/lircexec"
FILES_lirc-remotes = "${datadir}/lirc/remotes"


