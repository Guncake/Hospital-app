package pl.kgorak.hospitalapp.data

object DataObj {
    private var qrcode: String = ""
    private var token: String = ""
    private var loggedUser: String = ""

    public fun getQRcode(): String {
        return qrcode
    }
    public fun getToken(): String {
        return token
    }
    public fun setQRcode(qrc: String) {
        qrcode = qrc
    }
    public fun setToken(tok: String) {
        token = tok
    }
    public fun getUser(): String {
        return loggedUser
    }
    public fun setUser(usr: String) {
        loggedUser = usr
    }
}