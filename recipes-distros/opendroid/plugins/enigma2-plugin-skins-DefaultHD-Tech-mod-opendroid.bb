DESCRIPTION = "Enigma2 Skin DefaultHD-Tech-mod-opendroid"
MAINTAINER = "Opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r3"

SRC_URI="git://github.com/opendroid-Team/DefaultHD-Tech-mod-opendroid.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}
