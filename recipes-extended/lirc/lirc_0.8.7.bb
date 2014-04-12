DESCRIPTION = "LIRC is a package that allows you to decode and send infra-red signals of many commonly used remote controls."
DESCRIPTION_append_lirc = " This package contains the lirc daemon, libraries and tools."
DESCRIPTION_append_lirc-exec = " This package contains a daemon that runs programs on IR signals."
DESCRIPTION_append_lirc-remotes = " This package contains some config files for remotes."
SECTION = "console/network"
PRIORITY = "optional"
HOMEPAGE = "http://www.lirc.org"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel libusb"
RDEPENDS_lirc-exec = "lirc"
RRECOMMENDS_${PN} = "lirc-exec"

SRC_URI[md5sum] = "fb1dcddbc8ca3d03c7f3a2d40cf28b71"
SRC_URI[sha256sum] = "e431454f266c4ddc209c3cc84fc506cac6092eb5124b4f3dcc215c8584ea7a39"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

PR = "${INCPR}.2"

EXTRA_OECONF += "--with-kerneldir=${STAGING_KERNEL_DIR} --without-x --with-driver=userspace "

inherit autotools module-base update-rc.d

SRC_URI_append = " file://lirc-0.8.7-try_first_last_remote.diff;patch=1 \
    file://lirc-0.8.7-uinput-repeat-fix.diff;patch=1 \
    file://fix-libusb-config.patch;patch=1 \
    file://lircd.init \
    file://lircmd.init \
    file://lircexec.init \
    file://lircd_${MACHINE}.conf \
    "

INITSCRIPT_PACKAGES = "lirc lirc-exec"
INITSCRIPT_NAME = "lircd"
INITSCRIPT_PARAMS = "defaults 20"
INITSCRIPT_NAME_lirc-exec = "lircexec"
INITSCRIPT_PARAMS_lirc-exec = "defaults 21"

require lirc-config.inc

EXTRA_OEMAKE = 'SUBDIRS="daemons tools"'

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install ${WORKDIR}/lircd.init ${D}${sysconfdir}/init.d/lircd
	install ${WORKDIR}/lircexec.init ${D}${sysconfdir}/init.d/lircexec
        install -d ${D}${datadir}/lirc/
        cp -pPR ${S}/remotes ${D}${datadir}/lirc/
	rm -rf ${D}/dev
        rm -rf  ${D}/bin/pronto2lirc 
	install -m 0644 ${WORKDIR}/lircd_${MACHINE}.conf ${D}${sysconfdir}/lircd.conf
}

PACKAGES =+ "lirc-exec lirc-remotes"

FILES_${PN}-dbg += "${bindir}/.debug ${sbindir}/.debug"
FILES_${PN}-dev += "${libdir}/liblirc_client.so"
FILES_${PN} = "${bindir} ${sbindir} ${libdir}/lib*.so.* ${sysconfdir} /var /run "
FILES_lirc-exec = "${bindir}/irexec ${sysconfdir}/init.d/lircexec"
FILES_lirc-remotes = "${datadir}/lirc/remotes"
