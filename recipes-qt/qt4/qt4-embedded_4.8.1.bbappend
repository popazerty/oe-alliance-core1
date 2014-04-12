DEPENDS := "${@oe_filter_out('^(mysql5|postgresql|sqlite)$', '${DEPENDS}', d)}"

SRC_URI += "file://0001-Qt-build-NPAPI-without-X11.patch \
            file://0002-Qt-Add-HbbTv-MIME-types.patch \
            file://0003-Qt-expose-WebKits-WebSecurityEnabled-setting.patch \
            file://0004-Qt-add-Q_GUI_EXPORT-to-QUpdateLaterEvent.patch"

SRC_URI_append_sh4 = " \
    file://0020-webkit-disable-the-fuse-ld-gold-flag.patch;patch=1 \
    file://qt-embedded-4.8.0-st200.patch;patch=1 \
    file://qt-embedded-4.8.0-sh4.patch;patch=1 \
    file://qt-embedded-4.8.0-armvX.patch;patch=1 \
    file://qt-embedded-4.8.0-mmap.patch;patch=1 \
    file://qt-embedded-4.8.0-add_SRC_OVER_rule.patch;patch=1 \
    file://qt-embedded-4.8.0-add_window_console_message_from_javaScript.patch;patch=1 \
    file://qt-embedded-4.8.0-reset_CacheLoadControlAttribute_to_default.patch;patch=1 \
    file://qt-embedded-4.8.0-adds_for_webkit_jit.patch;patch=1 \
    file://qt-embedded-4.8.0-directfb-enable-QT_NO_DIRECTFB_PREALLOCATED-QT_DIREC.patch;patch=1 \
    file://qt-embedded-4.8.0-imagedecoderqt-Use-DirectFB-to-load-single-frame-ima.patch;patch=1 \
    file://qt-embedded-4.8.0-st231_disable_fno-stack-protector.patch;patch=1 \
    file://qt-embedded-4.8.0-Accelerate_QtWebKit_animated_images.patch;patch=1 \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

QT_CONFIG_FLAGS += "-nomake demos -nomake docs -nomake examples"

QT_GLIB_FLAGS = "-no-glib"
QT_IMAGEFORMAT_FLAGS += "-no-libmng"
QT_PHONON_FLAGS = "-no-phonon"
QT_QDBUS_FLAGS = "-no-qdbus"
QT_QT3SUPPORT_FLAGS = "-no-qt3support"
QT_SQL_DRIVER_FLAGS = "-no-sql-ibase -no-sql-mysql -no-sql-odbc -no-sql-psql -no-sql-sqlite2 -plugin-sql-sqlite -system-sqlite"
QT_WEBKIT_FLAGS = "-webkit"

QT_DECORATION_FLAGS = "-plugin-decoration-default -plugin-decoration-styled -plugin-decoration-windows"
QT_GFX_DRIVER_FLAGS = "-plugin-gfx-directfb -plugin-gfx-linuxfb -no-gfx-multiscreen -no-gfx-qvfb -no-gfx-transformed -no-gfx-vnc"
QT_KBD_DRIVER_FLAGS = "-plugin-kbd-linuxinput -no-kbd-tty -no-kbd-qvfb"
QT_MOUSE_DRIVER_FLAGS = "-qt-mouse-linuxinput -plugin-mouse-linuxtp -plugin-mouse-pc -no-mouse-qvfb -plugin-mouse-tslib"
