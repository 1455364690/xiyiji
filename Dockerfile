RUN mkdir /root/sunjh
RUN mkdir /root/sunjh/log
RUN cp -R /home/mooctest/sunjh/filebeat/filebeat-7.9.3-linux-x86_64 /root/sunjh/filebeat
RUN cp -R /home/mooctest/sunjh/packetbeat/packetbeat-7.9.3-linux-x86_64 /root/sunjh/packetbeat
RUN cp -R /home/mooctest/sunjh/metricbeat/metricbeat-7.9.3-linux-x86_64 /root/sunjh/metricbeat
RUN nohup /root/sunjh/filebeat/filebeat -e -c /root/sunjh/filebeat/filebeat.yml & > /root/sunjh/log/filebeat-nohup.out
RUN nohup /root/sunjh/packetbeat/packetbeat -e -c /root/sunjh/packetbeat/packetbeat.yml & > /root/sunjh/log/packetbeat-nohup.out
RUN nohup /root/sunjh/metricbeat/metricbeat -e -c /root/sunjh/metricbeat/metricbeat.yml & > /root/sunjh/log/metricbeat-nohup.out