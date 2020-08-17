package com.namics.oss.gradle.notification.send

import com.namics.oss.gradle.notification.DefaultTemplates.Companion.MAIL_START
import com.namics.oss.gradle.notification.Model
import com.namics.oss.gradle.notification.send.Sender
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.Message as MailMessage


/**
 * Send Mail messages.
 *
 * @author rgsell, Namics AG
 * @since 01.04.20 17:24
 */
class MailSender(
    override var template: String = MAIL_START,
    var to: String? = null,
    var from: String? = null,
    var subject: String? = null,
    var smtpHost: String? = null,
    var smtpPort: String = "25"
) : Sender {
    override fun sendNotification(model: Model) {
        val text = process(model)
        val properties = System.getProperties()

        properties.setProperty("mail.smtp.host", requireNotNull(smtpHost))
        properties.setProperty("mail.smtp.port", smtpPort)
        val session = Session.getDefaultInstance(properties)
        val mimeMessage = MimeMessage(session)
        mimeMessage.setFrom(InternetAddress(requireNotNull(from)))
        mimeMessage.addRecipients(
            MailMessage.RecipientType.TO,
            requireNotNull(to).split(",").map { email -> InternetAddress(email) }.toTypedArray()
        )
        mimeMessage.setSubject(requireNotNull(subject), "ISO-8859-1")
        mimeMessage.setText(text, "UTF-8")
        Transport.send(mimeMessage)
    }
}