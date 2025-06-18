import { QueryClient, QueryClientProvider } from "@tanstack/react-query"
import { GlobalStyle, AppContainer, MainContent } from "./styles/GlobalStyles"
import { Sidebar } from "./components/Sidebar/Sidebar"
import { ChatArea } from "./components/Chat/ChatArea"
import { InputArea } from "./components/Chat/InputArea"
import { useChatStore } from "./store/chatStore"

const queryClient = new QueryClient()

function ChatApp() {
  const { currentChatId, chats } = useChatStore()
  const currentChat = chats.find((chat) => chat.id === currentChatId)
  const hasMessages = currentChat && currentChat.messages.length > 0

  return (
    <>
      <GlobalStyle />
      <AppContainer>
        <Sidebar />
        <MainContent>
          <ChatArea />
          {hasMessages && <InputArea />}
        </MainContent>
      </AppContainer>
    </>
  )
}

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ChatApp />
    </QueryClientProvider>
  )
}
