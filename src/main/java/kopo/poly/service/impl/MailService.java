package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service("MailService")
public class MailService implements IMailService {

    final String host = "smtp.naver.com";
    final String user = "berrytreeplz@naver.com";
    final String password = "polypoly!@24";

    /** 메일 보내기  */
    @Override
    public int doSendMail(MailDTO pDTO) {
        // 로그 찍기
        log.info(this.getClass().getName()+"doSendMail Start!");

        // 메일 발송 성공 여부(성공 : 1, 실패 : 0)
        int res = 1;

        // 전달 받은 DTO로부터 데이터 가져오기
        if (pDTO == null) {
            pDTO = new MailDTO();
        }

        String toMail = CmmUtil.nvl(pDTO.getToMail()); // 받는사람
        log.info("Service toMail : "+toMail);

        Properties props = new Properties();
        props.put("mail.smtp.host", host); //javax 외부 라이브러리에 메일 보내는 사람 정보 설정
        props.put("mail.smtp.port", 587); //javax 외부 라이브러리에 메일 보내는 사람 정보 설정
        props.put("mail.smtp.auth", "true"); //javax 외부 라이브러리에 메일 보내는 사람 인증 여부 설정

        // 네이버 SMTP 서버 인증 처리 로직
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));

            //메일 제목
            message.setSubject(CmmUtil.nvl(pDTO.getTitle()));

            //메일 내용
            message.setText(CmmUtil.nvl(pDTO.getContents()));

            // 메일 발송
            Transport.send(message);

        } catch (MessagingException e) { // 메일 전송 관련 에러 다 잡기
            res = 0; // 메일 발송 실패
            log.info("[ERROR]"+this.getClass().getName()+"doSendMail : "+e);
        } catch (Exception e) { //모든 에러 잡기
            res = 0;
            log.info("[ERROR]"+this.getClass().getName()+"doSendMail : "+e);
        }

        log.info(this.getClass().getName()+"doSendMail End!");
        return res;
    }
}