import { WebSocketGateway, WebSocketServer } from '@nestjs/websockets';
import { OPEN, Server } from 'ws';

@WebSocketGateway(8080)
export class ItemWsGateway {
  @WebSocketServer()
  server: Server;

  broadcast(data: any): void {
    this.server.clients.forEach(client => {
      if (client.readyState === OPEN) {
        client.send(JSON.stringify(data));
      }
    });
  }
}
