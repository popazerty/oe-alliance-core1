# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "fulan"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'SHD-8900 Alien' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'Spark Revolution' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'pingulux' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'SPARK TRIPLEX'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'SPARK RELOADED'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'Alien 2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'Spark Triple' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'Amiko' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'SOGNO' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'argus' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'GOLDEN MEDIA'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'GOLDEN MEDIA'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'Amiko' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'SOGNO' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'enigma2'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'enigma2'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'enigma2' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'mtd5'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'mtd5'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'mtd5' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'mtd6'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'mtd6'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'mtd6' , '', d)}\
"

KERNEL_FILE = "uImage"
ROOTFS_FILE = "e2jffs2.img"

TARGET_ARCH = "sh4"
STLINUX_SH_UPD_SRPMS = "http://ftp.stlinux.com/pub/stlinux/2.4/updates/SRPMS"
STLINUX_SH_UPD_RPMS = "http://ftp.stlinux.com/pub/stlinux/2.4/updates/RPMS/sh4/"

GCCVERSION = "4.8%"

PREFERRED_PROVIDER_virtual/kernel = "linux-stm"
PREFERRED_VERSION_linux-libc-headers = "2.6.32"
PREFERRED_PROVIDER_directfb = "stm-directfb"
SERIAL_CONSOLE = "115200 ttyAS0"

DVBPROVIDER = "kernel"

USE_VT = "0"
MACHINE_FEATURES = "3dtv alsa hdmicec switchoff wifi kernel26 ext2 usbhost serial dvb-c singlecore"

KERNEL_IMAGETYPE = "uImage"

# save some extra space, kernel has its own mtd partion 
RDEPENDS_kernel-base = ""

JFFS2_ERASEBLOCK = "0x20000"
JFFS2_ENDIANNESS = "${@base_conditional('SITEINFO_ENDIANNESS', 'le', '--little-endian', '--big-endian', d)}"
EXTRA_IMAGECMD_jffs2 = "--pad ${JFFS2_ENDIANNESS} --eraseblock=${JFFS2_ERASEBLOCK} --no-cleanmarkers"

IMAGE_FSTYPES ?= "jffs2"

# enable this for debug
#IMAGE_FSTYPES += "tar.gz spark71xx-usbimg"
#INHERIT += "usb_image-spark71xx"

IMAGE_CLASSES += "usb_image-spark71xx"
INITRAMFS_FSTYPES = "ext2.gz spark-updt"

IMAGE_CMD_jffs2_append = " ; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.jffs2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.jffs2; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -rf ${IMAGEDIR}; \
    "
	
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
    stlinux24-sh4-stx7111-fdma-firmware \
    stlinux24-sh4-stmfb-firmware-stx7111 \
    stslave-fw \
    ustslave \
    lirc \
    fulan-dvb-modules \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    tdt-gst-plugin-dvbmediasink \
    "

OLDEST_KERNEL = "2.6.32"

include conf/machine/include/autoload-filesystems.inc

require conf/machine/include/tune-sh4.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"
