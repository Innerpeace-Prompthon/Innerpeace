import { QueryClient, QueryClientProvider } from "@tanstack/react-query"
import { GlobalStyle } from "./styles/GlobalStyles"
import { Sidebar } from "./components/Sidebar/Sidebar"
import { ChatArea } from "./components/Chat/ChatArea"
import { InputArea } from "./components/Chat/InputArea"
import { SplitViewToggle } from "./components/SplitView/SplitViewToggle"
import { useChatStore } from "./store/chatStore"
import {
  AppContainer,
  SidebarWrapper,
  ChatContainer,
  ChatContent,
  SplitContainer,
  SplitHeader,
  SplitContent,
} from "./styles/App.styles"

const queryClient = new QueryClient()

function ChatApp() {
  const { currentChatId, chats, showSplitView } = useChatStore()
  const currentChat = chats.find((chat) => chat.id === currentChatId)
  const hasMessages = currentChat && currentChat.messages.length > 0

  return (
    <>
      <GlobalStyle />
      <AppContainer>
        <SidebarWrapper $isHidden={showSplitView}>
          <Sidebar />
        </SidebarWrapper>
        
        <ChatContainer $hasSplitView={showSplitView}>
          <ChatContent>
            <ChatArea />
          </ChatContent>
          {hasMessages && <InputArea />}
        </ChatContainer>
        
        {showSplitView && (
          <SplitContainer>
            <SplitHeader>추가 정보</SplitHeader>
            <SplitContent>
              여기에 추가 콘텐츠가 표시됩니다
            </SplitContent>
          </SplitContainer>
        )}
        
        <SplitViewToggle />
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