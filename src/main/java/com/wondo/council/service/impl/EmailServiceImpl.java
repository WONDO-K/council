package com.wondo.council.service.impl;

import com.wondo.council.service.EmailService;
import com.wondo.council.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    private final RedisUtil redisUtil;

    // 메일로 전송되는 인증번호
    public static final String ePw = createKey();

    // 인증 번호를 담고 있는 메일을 전송하는 기능
    private MimeMessage createMessage(String to)throws Exception{
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("이메일 인증 테스트");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 행복주택 온라인 반상회입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 입력란에 정확히 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("raspic5874@gmail.com","wondok"));//보내는 사람

        return message;
    }

    // 메일로 전송할 인증번호 생성
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try {
            redisUtil.setDataExpire(ePw, to, 60 * 1L); // 유효시간 1분
            javaMailSender.send(message); // 메일 발송
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw; // 메일로 보냈던 인증 코드를 서버로 리턴
    }
    @Override
    public Boolean verifyEmail(String key) throws ChangeSetPersister.NotFoundException {
        String memberEmail = redisUtil.getData(key);
        if (memberEmail == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        redisUtil.deleteData(key);
        log.info("인증코드 : " + key + "가 유효시간 1분 만료 혹은 인증되어 삭제됩니다.");
        if (key.equals(ePw)){
            log.info("인증 번호가 일치합니다.");
            return true;
        }else {
            log.info("인증 번호가 일치하지 않습니다.");
            return false;
        }
    }

}